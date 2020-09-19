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
            Qindex: 0,
            fadeAwayState: false,
            skiped: { name: "skpied" },
            result: [],
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
        console.log(770, selected, option);
        this.saveAnswer(this.state.Qindex, selected, option);

        if (this.state.Qindex + 1 < this.state.questionLen) {
            this.setState({
                Qindex: this.state.Qindex + 1
            });
        } else {
            console.log(886, this.state.answers);
            this.props.handleComplete(this.state.answers);
        }
    };

    render() {


        const { Qindex, questions } = this.state;

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
                            <QuestionType item={questions[Qindex]} handleNext={this.handleNext} />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}       
