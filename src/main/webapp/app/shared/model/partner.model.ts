export interface IPartner {
    id?: string;
    userId?: string;
    cniImage?: string;
    userImage?: string;
    references?: string;
    qualification?: string;
}

export class Partner implements IPartner {
    constructor(
        public id?: string,
        public userId?: string,
        public cniImage?: string,
        public userImage?: string,
        public references?: string,
        public qualification?: string
    ) {}
}
