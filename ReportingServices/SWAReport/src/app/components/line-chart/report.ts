export interface Report {
  topicName: string;
  data: ReportObj[]; 
}

export interface ReportObj {
  id: number;
  value: number;
  timestamp: number;
}

export interface ChartModel {
  type: string;
  name: string;
  showInLegend: boolean;
  yValueFormatString: string;
  dataPoints: ChartDTO[];
}

export interface ChartDTO {
  // x: number;
  label: string | null;
  y: number;
}