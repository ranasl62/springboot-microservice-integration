import { Component, OnInit } from '@angular/core';
import { TopicFilter } from '../../model/topicFilter';

@Component({
  selector: 'filter-n-chart',
  templateUrl: './filter-n-chart.component.html',
  styleUrls: ['./filter-n-chart.component.css']
})
export class FilterNChartComponent implements OnInit {
  topicName: string = "";
  startOnlyTime: string = "";
  endOnlyTime: string = "";
  choosedOnlyDate: Date = new Date();

  topicFilter: TopicFilter = {
    topicName: "",
    startDateTime: 0,
    endDateTime: 0,
    actionType: ""
  }

  constructor(){
    this.topicName = "";
    this.startOnlyTime = new Date().getHours()+":"+(new Date().getMinutes());
    this.endOnlyTime = new Date().getHours()+":"+new Date().getMinutes();
  }
  
  ngOnInit(): void {
    
  }

  getTopicFilterCondition(event: TopicFilter){
    this.topicFilter = event; 
  }
}
