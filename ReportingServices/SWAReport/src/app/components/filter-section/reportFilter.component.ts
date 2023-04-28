import {Component, Output, Input, EventEmitter, OnInit} from '@angular/core';
import { TopicFilter } from '../../model/topicFilter';

@Component({
    selector: 'report-filter',
    templateUrl: 'reportFilter.component.html',
    styleUrls: ['./reportFilter.component.css']
})
export class ReportFilter implements OnInit {
    @Input()
    topicName: string = "";

    @Input()
    startTime: string = "00:00";

    @Input()
    endTime: string = "00:00";
    
    @Input()
    choosedDate: Date = new Date();

    ngOnInit(): void {
    }

    @Output()
    getFilterCondition: EventEmitter<TopicFilter> = new EventEmitter<TopicFilter>();

    getTopicFilter(){
        let startDT = this.getDateTimeFromDateNTime(this.choosedDate, this.startTime);
        let endDT = this.getDateTimeFromDateNTime(this.choosedDate, this.endTime);

        let obj: TopicFilter = {
            startDateTime: startDT.getTime(),
            endDateTime: endDT.getTime(),
            actionType: "",
            topicName: this.topicName
        };
        return obj;
    }

    generateReport() {
        let obj = this.getTopicFilter();
        obj.actionType = "GenerateReport";
        this.getFilterCondition.emit(obj);
    }

    downloadCsv(){
        let obj = this.getTopicFilter();
        obj.actionType = "DownloadCsv";
        this.getFilterCondition.emit(obj);
    }

    getStartTimeChanged(event: any){
        this.startTime = event;
    }

    getEndTimeChanged(event: any){
        this.endTime = event;
    }
    
    getDateChanged(event: any){
       this.choosedDate = event;
    }

    getTopicChanged(event: any){
        this.topicName = event;
    }
    
    getDateTimeFromDateNTime(date: Date, time: String){
        var timeTokens = time.split(':');
        return new Date(date.getFullYear(),date.getMonth(),date.getDate(), parseInt(timeTokens[0]), parseInt(timeTokens[1]), 0);
    }
}