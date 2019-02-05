import { Moment } from 'moment';

export const enum JobType {
    ELECTRICITE = 'ELECTRICITE',
    PEINTURE = 'PEINTURE',
    PLOMBERIE = 'PLOMBERIE'
}

export const enum JobStatus {
    NEW = 'NEW',
    INIT = 'INIT',
    ONGOING = 'ONGOING',
    CANCELLED = 'CANCELLED',
    CLOSED = 'CLOSED'
}

export interface IJobBid {
    id?: string;
    description?: string;
    type?: JobType;
    wishDate?: Moment;
    customerId?: string;
    location?: string;
    partnerId?: string;
    amount?: number;
    status?: JobStatus;
}

export class JobBid implements IJobBid {
    constructor(
        public id?: string,
        public description?: string,
        public type?: JobType,
        public wishDate?: Moment,
        public customerId?: string,
        public location?: string,
        public partnerId?: string,
        public amount?: number,
        public status?: JobStatus
    ) {}
}
