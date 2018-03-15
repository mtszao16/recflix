import React, { Component } from "react";
import { HashRouter, Route } from "react-router-dom";

import "./App.css";
import "../node_modules/video-react/dist/video-react.css";
import Display from "./components/Display";

const App = props => (
  <div className="container">
    <HashRouter>
      <Route path="/" component={Display} />
    </HashRouter>
  </div>
);

export default App;
