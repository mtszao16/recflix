import React, { Component } from 'react';
import { Player, ControlBar, PlaybackRateMenuButton } from 'video-react';

import CustomForwardReplayControl from './CustomForwardReplayControl';
import CustomPlayToggle from './CustomPlayToggle';
import CustomProgressControl from './CustomProgressControl';
const CustomForwardControl = CustomForwardReplayControl('forward');
const CustomReplayControl = CustomForwardReplayControl('replay');

class Display extends Component {
  render() {
    return (
      <div className="container">
        <div className="video-player">
          <div className="col-sm-12">
            <Player
              disableDefaultControls
              src="http://res.cloudinary.com/guptautkarsh/video/upload/v1519069513/Binary_tree_traversal_-_breadth-first_and_depth-first_strategies.mp4"
            >
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
        <div class="star-rating">
          <input type="radio" id="5-stars" name="rating" value="5" />
          <label for="5-stars" class="star">
            &#9733;
          </label>
          <input type="radio" id="4-stars" name="rating" value="4" />
          <label for="4-stars" class="star">
            &#9733;
          </label>
          <input type="radio" id="3-stars" name="rating" value="3" />
          <label for="3-stars" class="star">
            &#9733;
          </label>
          <input type="radio" id="2-stars" name="rating" value="2" />
          <label for="2-stars" class="star">
            &#9733;
          </label>
          <input type="radio" id="1-star" name="rating" value="1" />
          <label for="1-star" class="star">
            &#9733;
          </label>
        </div>
      </div>
    );
  }
}

export default Display;
