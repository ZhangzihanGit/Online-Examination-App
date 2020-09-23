import React from 'react';
import TakeExam from "./takeExam";
import ManageExam from "../manageExam/manageExam";



export default class Subject extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isStarted: false,
            isManaging: false,
            code: props.match.params.code
        };
    }

    handleStart = () => {

        this.setState({
            isManaging: false,
            isStarted: true
        });
    }


    handleManage = () => {
        this.setState({
            isstarted: false,
            isManaging: true
        });
    }

    render() {
        const { isStarted, isManaging, code } = this.state;
        if (isStarted) {
            return (
                <div>
                    <TakeExam />
                </div >
            );

        } else if (isManaging) {
            return (
                <div>
                    Managing exam
                    <ManageExam code={this.state.code} />
                </div >
            );

        } else {
            return (
                <div>
                    <h1>{code}</h1>
                    <button onClick={this.handleStart} className="buttonExam green">Take Exam</button>
                    <button onClick={this.handleManage} className="buttonExam blue">Exam Management</button>
                </div >
            );
        }
    }
}