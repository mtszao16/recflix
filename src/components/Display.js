import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
  Player,
  ControlBar,
  PlaybackRateMenuButton,
  BigPlayButton
} from 'video-react';

import CustomForwardReplayControl from './CustomForwardReplayControl';
import CustomPlayToggle from './CustomPlayToggle';
import CustomProgressControl from './CustomProgressControl';
import CustomBigPlayButton from './CustomBigPlayButton';
const CustomForwardControl = CustomForwardReplayControl('forward');
const CustomReplayControl = CustomForwardReplayControl('replay');

class Display extends Component {
  render() {
    return (
      <div>
        <div className="col-sm-12">
          <Player
            disableDefaultControls
            src="http://res.cloudinary.com/guptautkarsh/video/upload/v1519069513/Binary_tree_traversal_-_breadth-first_and_depth-first_strategies.mp4"
          >
            <CustomBigPlayButton position="center" />
            <ControlBar autoHide>
              <CustomReplayControl seconds={5} order={2.1} />
              <CustomForwardControl seconds={5} order={3.1} />
              <CustomProgressControl order={6} />
              <PlaybackRateMenuButton
                rates={[5, 3, 1.5, 1, 0.5, 0.1]}
                order={7.1}
              />
              <CustomPlayToggle />
            </ControlBar>
          </Player>
        </div>
      </div>
    );
  }
}

export default Display;
