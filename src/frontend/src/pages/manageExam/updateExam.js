import React from 'react';
import ExamDemo from "./examDemo";
import EachQuestion from "./eachQuestion";
import { useSelector, useDispatch } from 'react-redux';
import { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';

import * as api from '../../api/subject';

// import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import {
    Box,
    // Button,
    // DialogTitle,
    // DialogContent,
    // TextField,
    // DialogContentText,
    // MenuItem,
    // Select,
    // FormControl,
    // InputLabel
} from '@material-ui/core';


// export default class UpdateExam extends React.Component {
export default function UpdateExam() {


    const { identity } = useSelector(state => state.user);
    const { examList } = useSelector(state => state.subject);
    const { examId } = useParams();

    const [currentExam, setCurrentExam] = useState(null);
    const [updatedQuestions, setUpdatedQuestions] = useState({});






    const handleSubmit = async () => {
        let questions = [];
        let dataOut = {}
        const changedQuestions = Object.values(updatedQuestions);
        changedQuestions.forEach((value, key) => { questions.push(value) });

        dataOut["subjectId"] = currentExam.subjectId;
        dataOut["examId"] = currentExam.examId;
        dataOut["questions"] = questions;


        alert("Request POST:");
        alert(JSON.stringify(dataOut));
        api.updateExam(dataOut);
    }

    const handleSave = async (newQuestion) => {

        updatedQuestions[newQuestion.questionId] = newQuestion;
        setUpdatedQuestions(updatedQuestions);
        alert("Change staged, click update exam button to push to the database")

    }


    const handleDelete = async (Qid) => {
        alert("API not ready yet")
        // let updatedQuestions = this.state.updatedQuestions;
        // let exam = this.state.exam;
        // let qIndex = 0;
        // let qLocation = 0;
        // exam.questions.forEach(question => {
        //     if (question.Qid === Qid) {
        //         qLocation = qIndex;
        //     }
        //     qIndex += 1;
        // });

        // exam.questions.splice(qLocation, 1);

        // updatedQuestions[Qid] = "DELETED";
        // await this.setState({
        //     updatedQuestions: updatedQuestions,
        //     exam: exam,
        //     isLoaded: false
        // });

        // this.setState({
        //     isLoaded: true
        // });
    }


    useEffect(() => {
        async function fetchData() {
            const result = await api.getExam({ "examId": examId });
            setCurrentExam(result.data);
        }
        fetchData();
    }, []);



    if (currentExam === null) {
        return (
            <div>Loading...</div>
        );
    } else {
        return (
            <div>
                <h2>Exam ID: {currentExam.examId}</h2>

                {currentExam.questions.map((question, index) => (
                    <Box key={index}>
                        <EachQuestion question={question} handleSave={handleSave} handleDelete={handleDelete} />
                    </Box>
                ))}
                <div className=" center_align">
                    <button onClick={handleSubmit} className="buttonExam green">Update Exam</button>
                </div>
            </div >
        );
    }
}