import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';

import './App.css';
import '../node_modules/video-react/dist/video-react.css';
import Display from './components/Display';
import Header from './components/Header';
import Login from './components/Login';

const App = props => (
  <React.Fragment>
    <Header />
    <div className="container">
      <Switch>
        <Route exact path="/login" component={Login} />
        {/* <Route exact path="/signup" component={SignUp} /> */}
        <Route path="/" component={Display} />
      </Switch>
    </div>
  </React.Fragment>
);

export default App;
