import React, { Component } from "react";
import "./exam.css";
import "antd/dist/antd.css";
import QuestionType from "./questionType";

export default class ExamQuestions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            questions: null,
            questionLen: null,
            submitAble: false,
            Qindex: 0,
            answers: {}
        };
    }

    componentDidMount() {
        this.setState({
            questions: this.props.questionsIn,
            questionLen: this.props.questionsIn.length
        });
    }




    saveAnswer = (Qindex, selected, option) => {
        let answers = this.state.answers;
        answers[Qindex] = selected;

        this.setState({
            answers: answers
        });
    }

    handleNext = (selected, option) => {
        this.saveAnswer(this.state.Qindex, selected, option);

        if (this.state.Qindex + 1 < this.state.questionLen) {
            this.setState({
                Qindex: this.state.Qindex + 1
            });

        } else {

            this.setState({
                submitAble: true
            });
            alert("You have reached the end of the exam, you can click submit to submit the exam" +
                "Or you can  can go back already answered questions to update them by clicking previous.");
        }
    }

    handleComplete = () => {
        this.props.handleComplete(this.state.answers);
    }

    handlePrevious = (selected, option) => {
        if (this.state.Qindex > 0) {
            this.setState({
                Qindex: this.state.Qindex - 1
            });
        } else {
            alert("You hace reached the first question!")
        }
    };

    render() {


        const { Qindex, questions, submitAble } = this.state;

        if (questions == null) {
            return (
                <div className="cards-container cards-container-checkbox">
                    Loading...
                </div>
            );
        }

        return (
            <div className="cards-container cards-container-checkbox">
                <div className="cards-wrapper">
                    <div className="cards-list">
                        <div className="card-container" >
                            <div className="counter">
                                <h5>{questions[Qindex].Qid}/{questions.length}</h5>
                            </div>

                            <h4 className="primary-card-text">{questions[Qindex].questionText}</h4>

                            <QuestionType item={questions[Qindex]} handleNext={this.handleNext} index={Qindex}
                                handlePrevious={this.handlePrevious} />
                            {submitAble ?
                                <div>
                                    <br /> <button onClick={this.handleComplete} className="buttonExam green">Submit</button>
                                </div>
                                : null}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}       
