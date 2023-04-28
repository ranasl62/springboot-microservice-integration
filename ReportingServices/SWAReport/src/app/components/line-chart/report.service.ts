import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { DatePipe } from '@angular/common';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Report, ReportObj, ChartModel, ChartDTO } from './report';
import { HttpErrorHandler, HandleError } from '../../http-error-handler.service';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

@Injectable()
export class ReportService {
    baseUrl = 'http://10.200.9.75:8081/report';

    byTimeRangeUrl = this.baseUrl + '/';
    byTopicUrl = this.baseUrl + '/topic/';
    topicsUrl = this.baseUrl + '/topics';
    csvReportUrl = this.baseUrl + '/csv';

    private handleError: HandleError;

    constructor(
        private http: HttpClient,
        private datePipe: DatePipe,
        httpErrorHandler: HttpErrorHandler) {
        this.handleError = httpErrorHandler.createHandleError('ReportService');
    }

    getReportByTimeRange(topicName: string, from: number, to: number): Observable<Report[]> {
        let params = new HttpParams();
        params = params.set('from', from);
        params = params.set('to', to);
        params = params.set('topicName', topicName);

        const options = from && to ? { params: params } : {};

        return this.http.get<Report[]>(this.byTimeRangeUrl, options)
            .pipe(
                catchError(this.handleError('getReportByTimeRange', []))
            );
    }

    getReportByTopic(topicName: string): Observable<Report[]> {
        let url = this.byTopicUrl + topicName;
        return this.http.get<Report[]>(url)
            .pipe(
                catchError(this.handleError('getReportByTopic', []))
            );
    }

    generateCsv(topicName: string, from: number, to: number): void {
        let csvDownloadUrl = this.csvReportUrl + '?from=' + from + '&to=' + to + '&topicName=' + topicName;
        window.open(csvDownloadUrl, '_blank');
    }

    getTopics(): Observable<string[]> {
        return this.http.get<string[]>(this.topicsUrl)
            .pipe(
                catchError(this.handleError('getTopics', []))
            );
    }

    convertToChartModel(reports: Report[]): ChartModel[] {
        let chartModelArr: ChartModel[] = [];

        for (let item of reports) {
            let dataPoints: ChartDTO[] = [];

            for (let dataItem of item.data) {
                let chartDTO: ChartDTO = {
                    // x: dataItem.timestamp,
                    label: this.datePipe.transform(dataItem.timestamp, 'HH:mm:ss'),
                    y: dataItem.value
                };

                dataPoints.push(chartDTO);
            }

            let chartModel: ChartModel = {
                type: "line",
                name: item.topicName,
                showInLegend: true,
                yValueFormatString: "#,###",
                dataPoints: dataPoints
            };

            chartModelArr.push(chartModel);
        }

        return chartModelArr;
    }

}