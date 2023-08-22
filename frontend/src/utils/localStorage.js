class LocalStorage {
  constructor() {}

  static setItem(key, value) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(key, value);
    }
  }

  static getItem(key) {
    if (typeof window !== 'undefined') {
      return localStorage.getItem(key);
    }
    // window객체 localStorage, sessionStorage는 값이 없을때 null
    return null;
  }

  static removeItem(key) {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(key);
    }
  }
}

export default LocalStorage;
