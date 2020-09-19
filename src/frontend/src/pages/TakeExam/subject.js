import React from 'react';
import { NavLink } from "react-router-dom";
import TakeExam from "./takeExam";



export default class Subject extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isStarted: false,
            code: props.match.params.code
        };
    }

    handleStart = () => {
        console.log(888)
        this.setState({
            isStarted: true
        });
    }

    render() {
        const { isStarted, code } = this.state;
        if (isStarted) {
            return (
                <div>
                    <TakeExam />
                </div >
            );

        } else {
            return (
                <div>
                    <h1>{this.state.code}</h1>
                    <button onClick={this.handleStart}>Take Exam</button>
                </div >
            );
        }
    }
}