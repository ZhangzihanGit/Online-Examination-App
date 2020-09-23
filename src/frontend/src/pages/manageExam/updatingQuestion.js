import React from 'react';


import {
    Button,
    DialogTitle,
    DialogContent,
    TextField,
    DialogContentText,
    MenuItem,
    Select,
    FormControl,
    InputLabel
} from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';


export default class UpdatingQuestion extends React.Component {
    constructor(props) {
        super();
        this.state = {
            question: props.item,
            questionType: props.item.questionType,
            selected: null,
            option: null
        };
    }

    handleSelect = (event) => {
        this.setState({
            questionType: event.target.value
        });
    }

    handleExameQuestion = async (event) => {
        // the following call will stop the form from submitting
        event.preventDefault();

        // get the data from the form
        const dataForm = new FormData(event.target);
        var formObject = {};
        dataForm.forEach((value, key) => { formObject[key] = value; });
        formObject.Qid = this.state.question.Qid;

        this.props.handleSave(formObject);

    }





    render() {
        const { question, questionType } = this.state;

        return (
            <div>
                <form onSubmit={this.handleExameQuestion}>

                    <DialogContent>
                        <TextField
                            multiline
                            fullWidth
                            name="questionText"
                            required
                            rows={5}
                            defaultValue={question.questionText}
                            label="New Question Description"
                            variant="outlined"
                        />
                        <DialogContentText />
                        <TextField
                            name="mark"
                            required
                            type="number"
                            defaultValue={question.mark}
                            label="New Question Mark"
                            variant="outlined"
                        />
                        <DialogContentText />
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">New Question Type</InputLabel>
                            <Select
                                required
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="questionType"
                                defaultValue={question.questionType}
                                onChange={this.handleSelect}
                            >
                                <MenuItem value={'MCQ'}>Multiple Choice</MenuItem>
                                <MenuItem value={'shortAnswer'}>Short Answer</MenuItem>
                            </Select>
                        </FormControl>

                        {questionType === 'MCQ' ? (
                            <div>
                                <DialogContentText >Enter selection options below, separate each option by "///"</DialogContentText>
                                <TextField
                                    multiline
                                    fullWidth
                                    name="selectionOptions"
                                    defaultValue={question.selectionOptions}
                                    required
                                    rows={5}
                                    label="Selection Options"
                                    variant="outlined"
                                />
                            </div>
                        ) : null}

                    </DialogContent>
                    <div className="center_align">
                        <Button type="submit" variant="contained" color="primary" size="small" startIcon={<SaveIcon />}                        >
                            Save
                        </Button>
                    </div>
                </form>
            </div >);


    }
}





