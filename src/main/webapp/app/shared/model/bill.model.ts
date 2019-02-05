export const enum BillStatus {
    NEW = 'NEW',
    VALIDATION = 'VALIDATION',
    VALIDATED = 'VALIDATED',
    SENT = 'SENT',
    CONFIRMED = 'CONFIRMED',
    CANCELLED = 'CANCELLED',
    PAID = 'PAID'
}

export interface IBill {
    id?: string;
    designation?: string;
    quantity?: number;
    unitPrice?: number;
    jobid?: string;
    status?: BillStatus;
}

export class Bill implements IBill {
    constructor(
        public id?: string,
        public designation?: string,
        public quantity?: number,
        public unitPrice?: number,
        public jobid?: string,
        public status?: BillStatus
    ) {}
}
