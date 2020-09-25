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

import { getSubject } from "../../api/subject";


// export default class UpdateExam extends React.Component {
export default function UpdateExam() {


    const { identity } = useSelector(state => state.user);
    const { examList } = useSelector(state => state.subject);
    const { examId } = useParams();






    const handleSubmit = async () => {
        alert("Exam updated, message POST:");
        alert(JSON.stringify(this.state.updatedQuestions));
        window.location.reload();
    }

    const handleSave = async (newQuestion) => {
        let updatedQuestions = this.state.updatedQuestions;
        updatedQuestions[newQuestion.Qid] = newQuestion;
        this.setState({
            updatedQuestions: updatedQuestions
        });
        alert("Change applied. Don't forget to click UPDATE EXAM after apllying all changes");
    }


    const handleDelete = async (Qid) => {
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


    // useEffect(async () => {
    //     const result = await api.getExam({ "examId": examId });
    //     console.log(77777777777, result.data);
    // });


    const getExam = async () => {
        const result = await api.getExam({ "examId": examId });
        console.log(8888888888, result.data);
        return result.data;
    }

    const [currentExam, setCurrentExam] = useState(null);
    useEffect(() => {


        async function fetchData() {
            // You can await here
            const result = await api.getExam({ "examId": examId });
            console.log(77777777777, result.data);
            setCurrentExam(result.data);
            // ...
        }
        fetchData();
    }, []);



    if (currentExam === null) {
        console.log(778, currentExam)
        return (
            <div>Loading...</div>
        );
    } else {
        console.log(779, currentExam)
        // return (
        //     <div>Loading2...</div>
        // );
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