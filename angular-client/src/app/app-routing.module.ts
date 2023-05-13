import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SaleComponent } from './components/sale.component';

const routes: Routes = [
  {path: 'sale', component: SaleComponent},
  {path: '**', redirectTo: "sale", pathMatch: 'full'}
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule]
})

export class AppRoutingModule { }
