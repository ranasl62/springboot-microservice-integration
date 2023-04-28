import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
  selector: 'datepicker',
  templateUrl: './datepicker.component.html'
})
export class DatepickerComponent {
  @Output()
  getDate = new EventEmitter<Date>();

  date = new FormControl(new Date());

  changeDate(event:  MatDatepickerInputEvent<Date>){
    this.getDate.emit(event.value ? event.value : undefined);
  }

}
