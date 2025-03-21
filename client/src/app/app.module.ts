import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu.component';
import { PlaceOrderComponent } from './components/place-order.component';

import { ConfirmationComponent } from './components/confirmation.component';

const routes: Routes = [
  { path: '', component: MenuComponent },
  { path: 'place-order', component: PlaceOrderComponent },
  { path: 'confirmation', component: ConfirmationComponent },
];

@NgModule({
  declarations: [
    AppComponent, MenuComponent, PlaceOrderComponent, ConfirmationComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, RouterModule.forRoot(routes),
  ],
  providers: [ provideHttpClient() ],
  bootstrap: [AppComponent]
})
export class AppModule { }
