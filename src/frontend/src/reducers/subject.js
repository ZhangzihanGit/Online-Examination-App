import {
  DELETE_SUBJECT,
  GET_SUBJECT_LIST
} from '../constants/actions';

const initState = {
  // subjectList: [
  //   {
  //     id: 1,
  //     showName: 'SWEN90010',
  //     description: 'High Integrity Systems Engineering',
  //     cover: "https://picsum.photos/seed/1/300/180",
  //   }, {
  //     id: 2,
  //     showName: 'SWEN90004',
  //     description: 'Modelling Complex Software System',
  //     cover: "https://picsum.photos/seed/2/300/180",
  //   }, {
  //     id: 3,
  //     showName: 'SWEN90007',
  //     description: 'Software Design and Architecture',
  //     cover: "https://picsum.photos/seed/3/300/180",
  //   }, {
  //     id: 4,
  //     showName: 'COMP90043',
  //     description: 'Cryptography and Security',
  //     cover: "https://picsum.photos/seed/4/300/180",
  //   }, {
  //     id: 5,
  //     showName: 'COMP90048',
  //     description: 'Declarative Programming',
  //     cover: "https://picsum.photos/seed/5/300/180",
  //   }, {
  //     id: 6,
  //     showName: 'COMP30027',
  //     description: 'Machine Learning',
  //     cover: "https://picsum.photos/seed/6/300/180",
  //   }, {
  //     id: 7,
  //     showName: 'SWEN30006',
  //     description: 'Software Modelling and Design',
  //     cover: "https://picsum.photos/seed/7/300/180",
  //   }
  // ]
};

export default function reducer(state = initState, action) {

  switch (action.type) {
    case GET_SUBJECT_LIST:
      console.log(action.payload.subjectList)
      return {
        ...state,
        subjectList: action.payload.subjectList,
      }
    case DELETE_SUBJECT:
      return {
        ...state,
        subjectList: state.subjectList.filter(subject => action.payload !== subject),
      }
    default:
      return state;
  }
};