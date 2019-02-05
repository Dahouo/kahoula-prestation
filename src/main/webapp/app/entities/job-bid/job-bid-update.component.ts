import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IJobBid } from 'app/shared/model/job-bid.model';
import { JobBidService } from './job-bid.service';

@Component({
    selector: 'jhi-job-bid-update',
    templateUrl: './job-bid-update.component.html'
})
export class JobBidUpdateComponent implements OnInit {
    jobBid: IJobBid;
    isSaving: boolean;
    wishDate: string;

    constructor(protected jobBidService: JobBidService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobBid }) => {
            this.jobBid = jobBid;
            this.wishDate = this.jobBid.wishDate != null ? this.jobBid.wishDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.jobBid.wishDate = this.wishDate != null ? moment(this.wishDate, DATE_TIME_FORMAT) : null;
        if (this.jobBid.id !== undefined) {
            this.subscribeToSaveResponse(this.jobBidService.update(this.jobBid));
        } else {
            this.subscribeToSaveResponse(this.jobBidService.create(this.jobBid));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobBid>>) {
        result.subscribe((res: HttpResponse<IJobBid>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
