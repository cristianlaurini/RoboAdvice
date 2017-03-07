import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AppService {

  constructor(private http:Http) { }

  loginUser(user) {
    return this.http.post(AppConfig.url + 'loginUser', user)
      .map(response => response.json());
  }

  registerUser(user){
    return this.http.post(AppConfig.url + 'registerUser', user)
      .map(response => response.json());
  }
  
  getDefaultStrategySet() {
    return this.http.post(AppConfig.url + 'getDefaultStrategySet', {})
      .map(response => response.json());
  }
  
  getAssetClassSet() {
    return this.http.post(AppConfig.url + 'getAssetClassSet', {})
      .map(response => response.json());
  }

  getAssetSet(){
    return this.http.post(AppConfig.url + 'getAssetSet', {})
      .map(response => response.json());
  }
  
  getFinancialData(id, period){
    return this.http.post(AppConfig.url + 'getFinancialData', {id: id, period: period})
      .map(response => response.json());
  }
  
  getUserCurrentPortfolio(id, email, password){
    return this.http.post(AppConfig.url + 'getUserCurrentPortfolio', {id: id, email: email, password: password})
      .map(response => response.json());
  }
  
  addCapital(id, amount){
    return this.http.post(AppConfig.url + 'addCapital', {idUser: id, amount: amount})
      .map(response => response.json());
  }
  
  getCurrentCapital(user){
    return this.http.post(AppConfig.url + 'getCurrentCapital', user)
      .map(response => response.json());
  }
}
