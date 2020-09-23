import React from 'react';
import ExamDemo from "./examDemo";
import EachQuestion from "./eachQuestion";
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import {
    Box,
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

export default class UpdateExam extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isLoaded: false,
            examid: props.examid,
            exam: null,
            updatedQuestions: {}
        };
    }




    componentDidMount = () => {


        ExamDemo.forEach(exam => {
            if (exam.examid === this.props.examid) {
                this.setState({
                    exam: exam,
                    isLoaded: true
                });
            }
        });
    }

    handleSubmit = async () => {
        alert("Exam updated, message POST:");
        alert(JSON.stringify(this.state.updatedQuestions));
        window.location.reload();
    }


    handleSave = async (newQuestion) => {
        let updatedQuestions = this.state.updatedQuestions;
        updatedQuestions[newQuestion.Qid] = newQuestion;
        this.setState({
            updatedQuestions: updatedQuestions
        });
        alert("Change applied. Don't forget to click UPDATE EXAM after apllying all changes");
    }


    handleDelete = async (Qid) => {
        let updatedQuestions = this.state.updatedQuestions;
        let exam = this.state.exam;
        let qIndex = 0;
        let qLocation = 0;
        exam.questions.forEach(question => {
            if (question.Qid === Qid) {
                qLocation = qIndex;
            }
            qIndex += 1;
        });

        exam.questions.splice(qLocation, 1);

        updatedQuestions[Qid] = "DELETED";
        await this.setState({
            updatedQuestions: updatedQuestions,
            exam: exam,
            isLoaded: false
        });

        this.setState({
            isLoaded: true
        });
    }


    render() {
        const { isLoaded, examid, exam } = this.state;

        if (isLoaded === false) {
            return (
                <div>
                    <h1>Loading...</h1>
                </div >
            );
        } else {
            return (
                <div>
                    <h2>Exam ID: {examid}</h2>

                    {exam.questions.map((question, index) => (
                        <Box key={index}>
                            <EachQuestion question={question} handleSave={this.handleSave} handleDelete={this.handleDelete} />
                        </Box>
                    ))}
                    <div className=" center_align">
                        <button onClick={this.handleSubmit} className="buttonExam green">Update Exam</button>
                    </div>
                </div >
            );
        }

    }
}