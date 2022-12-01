import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProveedorFormService, ProveedorFormGroup } from './proveedor-form.service';
import { IProveedor } from '../proveedor.model';
import { ProveedorService } from '../service/proveedor.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html',
})
export class ProveedorUpdateComponent implements OnInit {
  isSaving = false;
  proveedor: IProveedor | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: ProveedorFormGroup = this.proveedorFormService.createProveedorFormGroup();

  constructor(
    protected proveedorService: ProveedorService,
    protected proveedorFormService: ProveedorFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
      if (proveedor) {
        this.updateForm(proveedor);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proveedor = this.proveedorFormService.getProveedor(this.editForm);
    if (proveedor.id !== null) {
      this.subscribeToSaveResponse(this.proveedorService.update(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(proveedor: IProveedor): void {
    this.proveedor = proveedor;
    this.proveedorFormService.resetForm(this.editForm, proveedor);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, proveedor.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.proveedor?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
