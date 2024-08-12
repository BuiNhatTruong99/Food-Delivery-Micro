const ClientStorage = class {
  set(key: string, value: any) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (e) {
      console.error('localStorage', e);
    }
  }

  get(key: string) {
    try {
      let dataValue = localStorage?.getItem(key);
      dataValue = dataValue ? JSON.parse(dataValue) : '';
      return dataValue;
    } catch (e) {
      console.error('localStorage', e);
    }
  }

  remove(key: string) {
    localStorage.removeItem(key);
  }

  clear() {
    localStorage.clear();
  }
};

export const clientStorage = new ClientStorage();
