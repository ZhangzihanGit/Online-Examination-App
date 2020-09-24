export const capitalizeFirstLetter = (str) => str.charAt(0).toUpperCase() + str.slice(1);

export const findSubjectByCode = (subjectList, subjectCode) => {
  return subjectList.find(subject => subject.subjectCode === subjectCode);
}