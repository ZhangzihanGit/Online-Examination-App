import { refresh } from 'less';
import React from 'react';
import { TextField } from '@material-ui/core';



export default class QuestionType extends React.Component {
    constructor(props) {
        super();
        this.state = {
            item: props.item,
            selected: null,
            option: null
        };
    }

    handleSelect = (index, option) => {
        this.setState({
            selected: index,
            option: option
        });
    }

    handleShortAnswer = async (event) => {
        // the following call will stop the form from submitting
        event.preventDefault();

        // get the data from the form
        const dataForm = new FormData(event.target);
        var formObject = {};
        dataForm.forEach((value, key) => { formObject[key] = value; });

        this.setState({
            item: this.props.item,
            selected: null
        });

        await this.props.handleNext(formObject.qResponse, 0);
        this.setState({
            item: this.props.item,
            selected: null
        });
    }

    handleNext = async () => {
        const { selected, option } = this.state;
        await this.props.handleNext(selected, option);
        this.setState({
            item: this.props.item,
            selected: null
        });
    }

    handlePrevious = async () => {
        const { selected, option } = this.state;
        await this.props.handlePrevious(selected, option);
        this.setState({
            item: this.props.item,
            selected: null
        });
    }

    render() {
        const { item, selected } = this.state;

        if (item.questionType == "MCQ") {
            return (
                <div className="questionContainer">
                    <div className="composite-scale-container">
                        <div className="option-container-vertical">
                            {item.selectionOptions.map((option, index) => (
                                <button
                                    key={index}
                                    onClick={() => this.handleSelect(index, option)}
                                    className="composite-option-button"
                                >
                                    {index === selected ? <span className="composite-circle top selected"></span> :
                                        <span className="composite-circle top empty"></span>
                                    }
                                    <span className="composite-label bottom">{option}</span>
                                </button>
                            ))}
                        </div>
                    </div>
                    <div>
                        <button onClick={this.handlePrevious} className="buttonExam">Previous</button>
                        <button onClick={this.handleNext} className="buttonExam blue">Confirm</button>
                    </div>
                </div>
            );
        } else if (item.questionType == "shortAnswer") {
            console.log(552);
            return (
                <div className="questionContainer">

                    <div className="composite-scale-container">
                        <form onSubmit={this.handleShortAnswer}>
                            <TextField
                                id="outlined-multiline-flexible"
                                name="qResponse"
                                multiline
                                fullWidth
                                rows={4}
                                label="Enter your response here..."
                                variant="outlined"
                            />
                            <div className="composite-scale-container">
                                <button onClick={this.handlePrevious} className="buttonExam">Previous</button>
                                <button className="buttonExam blue">Confirm</button>
                            </div>
                        </form>
                    </div>


                </div>
            );
        } else {
            return (
                <div className="questionContainer">
                    Sorry, this question type is not supported.
                </div>
            );
        }

    }
}