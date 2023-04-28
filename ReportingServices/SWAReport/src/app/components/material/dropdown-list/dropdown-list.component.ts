import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { ReportService } from './../../line-chart/report.service';

@Component({
  selector: 'dropdown-list',
  templateUrl: './dropdown-list.component.html',
  styleUrls: ['./dropdown-list.component.css'],
  providers: [ReportService]
})
export class DropdownListComponent implements OnInit {
  topics: string[] = ["Choose topic name"];
  chooseTopic: string = "";

  @Output()
  getTopicName: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit(): void {
    this.getTopics();
    this.chooseTopic = this.topics[0];
  }

  constructor(private reportService: ReportService) {
  }

  getTopics(): void {
    this.reportService.getTopics().subscribe(data => {
      this.topics.push(...data);
    });
  }

  getTopicChange(event: any) {
    console.log(event);
    if(event.value == this.topics[0]){
      this.getTopicName.emit("");
    } else {
      this.getTopicName.emit(event.value);
    }
    
    
  }

}
