import React from 'react';
import './App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import HeaderComponent from "./components/HeaderComponent/HeaderComponent";
import LoginComponent from "./components/LoginComponent/LoginComponent";
import DashboardComponent
  from "./components/DashboardComponent/DashboardComponent";
import ListTodosComponent from "./components/Todos/ListTodosComponent";
import ErrorComponent from "./components/ErrorComponent/ErrorComponent";
import FooterComponent from "./components/FooterComponent/FooterComponent";
import AuthenticatedRoute
  from "./components/AuthenticatedRoute/AuthenticatedRoute";
import TodoComponent from "./components/Todos/TodoComponent";
import AuthenticationService from "./services/AuthenticationService";

function App() {
  AuthenticationService.setupAxiosInterceptors();

  return (
    <div className="container">
      <BrowserRouter>
        <HeaderComponent/>
        <div className="content">
          <Switch>
            <Route exact path="/" component={LoginComponent}/>
            <Route path="/login" component={LoginComponent}/>
            <AuthenticatedRoute exact path="/dashboard/:name"
                                component={DashboardComponent}/>
            <AuthenticatedRoute exact path="/todos/:id" component={TodoComponent}/>
            <AuthenticatedRoute exact path="/todos" component={ListTodosComponent}/>
            <Route component={ErrorComponent}/>
          </Switch>
        </div>
        <FooterComponent/>
      </BrowserRouter>
    </div>
  );
}

export default App;
