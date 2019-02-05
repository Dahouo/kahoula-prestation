/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KahoulaPrestationTestModule } from '../../../test.module';
import { BillUpdateComponent } from 'app/entities/bill/bill-update.component';
import { BillService } from 'app/entities/bill/bill.service';
import { Bill } from 'app/shared/model/bill.model';

describe('Component Tests', () => {
    describe('Bill Management Update Component', () => {
        let comp: BillUpdateComponent;
        let fixture: ComponentFixture<BillUpdateComponent>;
        let service: BillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KahoulaPrestationTestModule],
                declarations: [BillUpdateComponent]
            })
                .overrideTemplate(BillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bill('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bill();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});