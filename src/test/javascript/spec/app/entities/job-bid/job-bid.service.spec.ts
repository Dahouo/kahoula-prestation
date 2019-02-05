/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobBidService } from 'app/entities/job-bid/job-bid.service';
import { IJobBid, JobBid, JobType, JobStatus } from 'app/shared/model/job-bid.model';

describe('Service Tests', () => {
    describe('JobBid Service', () => {
        let injector: TestBed;
        let service: JobBidService;
        let httpMock: HttpTestingController;
        let elemDefault: IJobBid;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(JobBidService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new JobBid('ID', 'AAAAAAA', JobType.ELECTRICITE, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, JobStatus.NEW);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        wishDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a JobBid', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID',
                        wishDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        wishDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new JobBid(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a JobBid', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        type: 'BBBBBB',
                        wishDate: currentDate.format(DATE_TIME_FORMAT),
                        customerId: 'BBBBBB',
                        location: 'BBBBBB',
                        partnerId: 'BBBBBB',
                        amount: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        wishDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of JobBid', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        type: 'BBBBBB',
                        wishDate: currentDate.format(DATE_TIME_FORMAT),
                        customerId: 'BBBBBB',
                        location: 'BBBBBB',
                        partnerId: 'BBBBBB',
                        amount: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        wishDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a JobBid', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
