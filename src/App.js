import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';

import './App.css';
import '../node_modules/video-react/dist/video-react.css';
import Display from './components/Display';
import Header from './components/Header';
import Auth from './components/Auth';
import Landing from './components/Landing';
import NoMatch from './components/NoMatch';

import { AUTH_TOKEN } from './utils/constants';

const App = props => {
  const authToken = localStorage.getItem(AUTH_TOKEN);

  return (
    <React.Fragment>
      <div className="main-bg">
        <img alt="Landing Page" src="./assets/images/movie-audience-alt2.jpg" />
      </div>
      <Header />
      {authToken ? (
        <Switch>
          <Route exact path="/" component={Landing} />
          <Route exact path="/login" render={() => <Redirect to="/" />} />
          <Route exact path="/movie" component={Display} />
          <Route component={NoMatch} />
        </Switch>
      ) : (
        <Switch>
          <Route exact path="/" render={() => <Redirect to="/login" />} />
          <Route exact path="/login" component={Auth} />
          <Route component={NoMatch} />
        </Switch>
      )}
    </React.Fragment>
  );
};

export default App;
