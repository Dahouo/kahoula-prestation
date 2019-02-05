export const enum Language {
    FRENCH = 'FRENCH',
    ENGLISH = 'ENGLISH'
}

export const enum UserType {
    CUSTOMER = 'CUSTOMER',
    PARTNER = 'PARTNER'
}

export interface IUsers {
    id?: string;
    firstName?: string;
    lastName?: string;
    password?: string;
    email?: string;
    phoneNumber?: string;
    location?: string;
    language?: Language;
    type?: UserType;
}

export class Users implements IUsers {
    constructor(
        public id?: string,
        public firstName?: string,
        public lastName?: string,
        public password?: string,
        public email?: string,
        public phoneNumber?: string,
        public location?: string,
        public language?: Language,
        public type?: UserType
    ) {}
}
