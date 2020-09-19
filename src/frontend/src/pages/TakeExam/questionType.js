import React from 'react';




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

    handleNext = () => {
        const { selected, option } = this.state;
        this.props.handleNext(selected, option);
        this.setState({
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
                    <button onClick={this.handleNext}>Next</button>
                </div>
            );
        } else {
            return (
                <div className="questionContainer">
                    Error, question type not supported.
                </div>
            );
        }

    }
}