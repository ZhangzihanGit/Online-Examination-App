export const capitalizeFirstLetter = (str) => str.charAt(0).toUpperCase() + str.slice(1);

export const findSubjectByCode = (subjectList, subjectCode) => {
  return subjectList.find(subject => subject.subjectCode === subjectCode);
};

export const findExamById = (examList, examId) => {
  return examList.find(exam => exam.examId === examId);
};

export const findAnswerById = (answers, questionId) => {
  return answers.find(answer => answer.questionId === questionId);
};

// split options to array of string: "["AAA","BBB","CCC"]" => ["AAA","BBB","CCC"]
export const splitOptions = (options) => JSON.parse(options);