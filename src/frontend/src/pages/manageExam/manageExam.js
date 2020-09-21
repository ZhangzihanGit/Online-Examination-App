import React from 'react';

// import questionDemo from "./questionDemo";
// import ExamQuestions from "./examQuestions";
import ExamOverview from "./examOverview";
import AddExam from "./addExam";




export default class manageExam extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isLoaded: false,
            isAdding: false
        };
    }

    handleAdd = () => {
        this.setState({
            isAdding: true
        });
    }

    render() {
        const { isLoaded, isAdding } = this.state;

        if (isLoaded === true) {
            return (
                <div>
                    <h1>Loading...</h1>
                </div >
            );
        } else if (isAdding === true) {
            return (
                <div>
                    <AddExam code={this.props.code} />
                </div >
            );
        } else {
            return (
                <div>

                    <ExamOverview />
                    <button onClick={this.handleAdd} className="buttonExam green">Add Exam</button>
                </div >
            );
        }

    }
}