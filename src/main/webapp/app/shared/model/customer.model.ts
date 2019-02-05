export interface ICustomer {
    id?: string;
    userId?: string;
}

export class Customer implements ICustomer {
    constructor(public id?: string, public userId?: string) {}
}
