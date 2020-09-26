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

export const isValidateNumber = (value, min, max) => {
  const regex = RegExp(/^[0-9]+$/);
  // only contains numbers
  if (regex.test(value)) {
    if (Number(value) <= max && Number(value) >= min) {
      return true;
    }
  }
  return false;
};

export const insertItem = (array, action) => {
  let newArray = array.slice()
  newArray.splice(action.index, 0, action.item)
  return newArray;
};

export const removeItem = (array, action) => {
  let newArray = array.slice()
  newArray.splice(action.index, 1)
  return newArray;
};

export const updateObjectInArray = (array, action) => {
  return array.map((item, index) => {
    if (index !== action.index) {
      // This isn't the item we care about - keep it as-is
      return item
    }

    // Otherwise, this is the one we want - return an updated value
    return {
      ...item,
      ...action.item
    }
  })
};