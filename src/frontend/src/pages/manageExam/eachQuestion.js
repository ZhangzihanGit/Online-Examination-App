import React from 'react';
// import ExamDemo from "./examDemo";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import UpdatingQuestion from "./updatingQuestion"

import {
    Box,
    Card,
    CardContent,
    IconButton,
    // DialogTitle,
    // DialogContent,
    // TextField,
    // DialogContentText,
    // MenuItem,
    // Select,
    // FormControl,
    // InputLabel
} from '@material-ui/core';

export default class EachQuestion extends React.Component {
    constructor(props) {
        super();
        this.state = {
            question: props.question,
            isEditing: false
        };
    }

    handleEdit = () => {
        this.setState({
            isEditing: !this.state.isEditing
        })
    }




    render() {
        const { question, isEditing } = this.state;

        return (
            <div>
                <Box p={1}><Card>
                    <CardContent>
                        <h1>{question.Qid}.{question.questionText.substring(0, 50)}......</h1>

                        <div className="right_align">
                            <IconButton color="secondary" onClick={() => this.props.handleDelete(question.Qid)} >
                                <DeleteIcon fontSize="small" />
                            </IconButton>

                            <IconButton color="secondary" onClick={() => this.handleEdit()}                            >
                                <EditIcon color="primary" />
                            </IconButton>
                        </div>
                        {isEditing ? <UpdatingQuestion item={question} handleSave={this.props.handleSave} /> : null}

                    </CardContent>
                </Card></Box>
            </div >
        );
    }
}