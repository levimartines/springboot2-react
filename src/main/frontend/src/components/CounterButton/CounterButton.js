import React, {Component} from 'react';
import PropTypes from 'prop-types';
import './CounterButton.css';

class CounterButton extends Component {

  // use arrow function to pass a parameter to a method in component
  render() {
    return (
      <div className="counterButton">
        <button onClick={() => this.props.decrementMethod(
          this.props.by)}>- {this.props.by}</button>
        <button onClick={() => this.props.incrementMethod(
          this.props.by)}>+ {this.props.by}</button>
      </div>
    );
  }

}

CounterButton.defaultProps = {
  by: 1,
}

CounterButton.propTypes = {
  by: PropTypes.number,
}

export default CounterButton;
