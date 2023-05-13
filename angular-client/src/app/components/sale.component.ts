import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SaleService } from '../services/sale.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ColDef } from 'ag-grid-community';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {
  sales : any = null;
  totalSaleUnits : number = 0;
  totalSaleRevenue: string = "";
  unit: string = "";
  rowData = [];
  displayedColumns : string[] = ['id', 'sale','orderDate', 'product', 'price', 'unit', 'store', 'address'];

  constructor(
    private router: Router,
    private route : ActivatedRoute,//Load dữ liệu từ url thông qua ActivatedRoute
    private saleService: SaleService,
  ) { }

  ngOnInit(): void {
    this._getAllSales();
  }

 // Each Column Definition results in one Column.
 public columnDefs: ColDef[] = [
  { headerName: 'ID', field: 'id', sortable: true },
  { headerName: 'Sale', field: 'sale', sortable: true },
  { headerName: 'Product', field: 'product', sortable: true, sort: 'asc' },
  { headerName: 'Order Date', field: 'orderDate', sortable: true },
  { headerName: 'Price', field: 'price', sortable: true },
  { headerName: 'Unit', field: 'unit', sortable: true },
  { headerName: 'Store', field: 'store', sortable: true },
  { headerName: 'Address', field: 'address', sortable: true }
];

// DefaultColDef sets props common to all Columns
public defaultColDef: ColDef = {
  sortable: true,
  filter: true,
};

  _getAllSales(){
    this.saleService.getAllSales().subscribe(
      (res) => {
        if(res.status == 200){
          this._formatDateTime(res?.body);
          console.log(this.sales);
          this.sales = res?.body;
          this.rowData = this.sales;

          // calculate the total sales units = sum of sales column
          this.totalSaleUnits = this.calculateTotalSaleUnits(this.rowData);

          // calculate total sales revenue = sum of (price x sale)
          this.totalSaleRevenue = this.numberWithCommas(this.calculateTotalSaleRevenue(this.rowData));
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  _timeStamp2Date(timestamp : any) {
    if(!timestamp) return null;
    return new Date(timestamp).toLocaleDateString('fr-CA');
  }

  _formatDateTime(userArr : any) {
    userArr.forEach((user: any) =>{
      user.orderDate = this._timeStamp2Date(user.orderDate);
    })
  }

  calculateTotalSaleUnits(data: any) {
    let sumOfSales: number = 0;
    data.forEach((row: any) => {
      sumOfSales+= row?.sale;
    })

    return sumOfSales;
  }

  calculateTotalSaleRevenue(data: any) {
    let sumOfSaleRevenue: number = 0;
    data.forEach((row: any) => {
      sumOfSaleRevenue+= row?.sale * row?.price;
    })

    this.unit = data[0]?.unit;

    return sumOfSaleRevenue;
  }

  numberWithCommas(input : number) {
    return input.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }
}

