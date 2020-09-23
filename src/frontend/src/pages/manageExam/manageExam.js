import React from 'react';

// import questionDemo from "./questionDemo";
// import ExamQuestions from "./examQuestions";
import ExamOverview from "./examOverview";
import AddExam from "./addExam";
import UpdateExam from "./updateExam";
// import "./exam.css";

export default class manageExam extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isAdding: false,
            isUpdating: false,
            targetExam: null
        };
    }

    handleAdd = () => {
        this.setState({
            isAdding: true,
            isUpdating: false
        });
    }

    startUpdate = (item) => {
        this.setState({
            isAdding: false,
            isUpdating: true,
            targetExam: item.examid
        });
    }

    render() {
        const { isAdding, isUpdating, targetExam } = this.state;

        if (isUpdating) {
            return (
                <div>
                    <UpdateExam examid={targetExam} />
                </div >
            );
        } else if (isAdding) {
            return (
                <div>
                    <AddExam code={this.props.code} />
                </div >
            );
        } else {
            return (
                <div>
                    <ExamOverview startUpdate={this.startUpdate} />
                    <div className=" center_align">
                        <button onClick={this.handleAdd} className="buttonExam green">Add Exam</button>
                    </div>
                </div >
            );
        }

    }
}