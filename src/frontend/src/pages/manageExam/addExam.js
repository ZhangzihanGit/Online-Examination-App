import React from 'react';

import {
    DialogTitle,
    DialogContent,
    TextField,
    DialogContentText,
    MenuItem,
    Select,
    FormControl,
    InputLabel
} from '@material-ui/core';

export default class AddExam extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isLoaded: true,
            isAddingQuestions: false,
            showName: null,
            description: null,
            subjectid: props.code,
            Qid: 1,

            questions: [],
            questionType: null
        };
    }



    handleSubmit = async () => {
        let newExam = {}
        newExam["showName"] = this.state.showName;
        newExam["description"] = this.state.description;
        newExam["subjectid"] = this.state.subjectid;
        newExam["questions"] = this.state.questions;

        alert("New exam created");
        alert(JSON.stringify(newExam));
        window.location.reload();
    }


    handleExameMeta = async (event) => {
        // the following call will stop the form from submitting
        event.preventDefault();

        // get the data from the form
        const dataForm = new FormData(event.target);
        var formObject = {};
        dataForm.forEach((value, key) => { formObject[key] = value; });

        this.setState({
            description: formObject.description,
            showName: formObject.showName,
            isAddingQuestions: true
        });


    }



    handleExameQuestion = async (event) => {

        event.preventDefault();

        const dataForm = new FormData(event.target);
        var formObject = {};
        dataForm.forEach((value, key) => { formObject[key] = value; });

        let selectionOptions = []
        let questions = this.state.questions;

        formObject.Qid = this.state.Qid;

        if (formObject.questionType === "multiplechoice") {
            selectionOptions = formObject.selectionOptions.split("///");
            formObject.selectionOptions = selectionOptions
        }

        questions.push(formObject);
        await this.setState({
            questions: questions,
            Qid: this.state.Qid + 1,
            isLoaded: false
        });

        this.setState({
            isLoaded: true
        });
    }

    handleSelect = (event) => {
        this.setState({
            questionType: event.target.value
        });
    }


    render() {
        const { isLoaded, subjectid, isAddingQuestions, Qid, questionType } = this.state;

        if (isLoaded === false) {
            return (
                <div>
                    <h1>Loading...</h1>
                </div >
            );
        } else if (isAddingQuestions) {
            return (
                <div>
                    <form onSubmit={this.handleExameQuestion}>
                        <DialogTitle id="form-dialog-title">Question {Qid}</DialogTitle>
                        <DialogContent>
                            <TextField
                                multiline
                                fullWidth
                                name="questionText"
                                required
                                rows={2}
                                label="Question Description"
                                variant="outlined"
                            />
                            <DialogContentText />
                            <TextField
                                name="mark"
                                required
                                type="number"
                                label="Question Mark"
                                variant="outlined"
                            />
                            <DialogContentText />
                            <FormControl fullWidth>
                                <InputLabel id="demo-simple-select-label">Question Type</InputLabel>
                                <Select
                                    required
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    defaultValue="multiplechoice"
                                    name="questionType"
                                    onChange={this.handleSelect}
                                >
                                    <MenuItem value={'multiplechoice'}>Multiple Choice</MenuItem>
                                    <MenuItem value={'shortAnswer'}>Short Answer</MenuItem>
                                </Select>
                            </FormControl>

                            {questionType === 'multiplechoice' ? (
                                <div>
                                    <DialogContentText >Enter selection options below, separate each option by "///"</DialogContentText>
                                    <TextField
                                        multiline
                                        fullWidth
                                        name="selectionOptions"
                                        required
                                        rows={5}
                                        label="Selection Options"
                                        variant="outlined"
                                    />
                                    {/* <TextField label="option1" name="option1" variant="outlined" />
                                    <TextField label="option2" name="option2" variant="outlined" />
                                    <TextField label="option3" name="option3" variant="outlined" />
                                    <TextField label="option4" name="option4" variant="outlined" />
                                    <TextField label="option5" name="option5" variant="outlined" />
                                    <TextField label="option6" name="option6" variant="outlined" /> */}
                                </div>
                            ) : null}

                        </DialogContent>
                        <div className="center_align">
                            <button className="buttonExam green">Next Question</button>
                        </div>
                    </form>
                    <div className="center_align padding_top">
                        <button className="buttonExam blue" onClick={this.handleSubmit}>Submit</button>
                    </div>
                </div >
            );
        } else {
            return (
                <div>
                    <form onSubmit={this.handleExameMeta}>
                        <DialogTitle id="form-dialog-title">{subjectid}: New Exam</DialogTitle>
                        <DialogContent>
                            <TextField
                                required
                                fullWidth
                                label="Exam Display Name"
                                name="showName"
                                variant="outlined"
                            />

                            <DialogContentText />
                            <TextField
                                multiline
                                fullWidth
                                name="description"
                                required
                                rows={4}
                                label="Exam Description"
                                variant="outlined"
                            />
                        </DialogContent>
                        <div className="center_align">
                            <button className="buttonExam green right">Add Questions</button>
                        </div>
                    </form>
                </div >
            );
        }

    }
}