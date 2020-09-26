
/**
 * Store redux state in localStorage, so that refresh the browser we will lost the state
 * https://adrianarlett.gitbooks.io/idiomatic-redux-by-dan-abramov/content/persisting-the-state-to-the-local-storage.html
 */
export const loadState = () => {
  try {
    const serializedState = localStorage.getItem('state');
    if (serializedState === null) {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

export const saveState = (state) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem('state', serializedState);
  } catch (err) {
    // Ignore write errors.
  }
};