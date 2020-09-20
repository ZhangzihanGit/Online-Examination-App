import React from 'react';

import questionDemo from "./questionDemo";
import ExamQuestions from "./examQuestions";




export default class TakeExam extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isCompleted: false,
            questions: questionDemo,
            answers: null
        };
    }

    handleComplete = (answers) => {
        this.setState({
            isCompleted: true,
            answers: answers
        });
    }

    render() {
        const { isCompleted, answers } = this.state;

        if (isCompleted) {
            return (
                <div>
                    <h1>Congradualtions! You answers have been saved and submitted successfully.</h1>
                    {JSON.stringify(answers)}
                </div >
            );

        } else {
            return (
                <div>
                    <h1>Exam Started</h1>
                    <ExamQuestions
                        questionsIn={questionDemo}
                        handleComplete={this.handleComplete}
                    />
                </div >
            );
        }

    }
}