import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'infraestructura',
        data: { pageTitle: 'sistemasAdmApp.infraestructura.home.title' },
        loadChildren: () => import('./infraestructura/infraestructura.module').then(m => m.InfraestructuraModule),
      },
      {
        path: 'proveedor',
        data: { pageTitle: 'sistemasAdmApp.proveedor.home.title' },
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.ProveedorModule),
      },
      {
        path: 'despliegue-infraestructura-troncal-distribucion',
        data: { pageTitle: 'sistemasAdmApp.despliegueInfraestructuraTroncalDistribucion.home.title' },
        loadChildren: () =>
          import('./despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.module').then(
            m => m.DespliegueInfraestructuraTroncalDistribucionModule
          ),
      },
      {
        path: 'despliegueinfraestructuradispersion',
        data: { pageTitle: 'sistemasAdmApp.despliegueinfraestructuradispersion.home.title' },
        loadChildren: () =>
          import('./despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.module').then(
            m => m.DespliegueinfraestructuradispersionModule
          ),
      },
      {
        path: 'pozo',
        data: { pageTitle: 'sistemasAdmApp.pozo.home.title' },
        loadChildren: () => import('./pozo/pozo.module').then(m => m.PozoModule),
      },
      {
        path: 'autorizaciones',
        data: { pageTitle: 'sistemasAdmApp.autorizaciones.home.title' },
        loadChildren: () => import('./autorizaciones/autorizaciones.module').then(m => m.AutorizacionesModule),
      },
      {
        path: 'registro-inspecciones',
        data: { pageTitle: 'sistemasAdmApp.registroInspecciones.home.title' },
        loadChildren: () => import('./registro-inspecciones/registro-inspecciones.module').then(m => m.RegistroInspeccionesModule),
      },
      {
        path: 'foto-pozo',
        data: { pageTitle: 'sistemasAdmApp.fotoPozo.home.title' },
        loadChildren: () => import('./foto-pozo/foto-pozo.module').then(m => m.FotoPozoModule),
      },
      {
        path: 'pago',
        data: { pageTitle: 'sistemasAdmApp.pago.home.title' },
        loadChildren: () => import('./pago/pago.module').then(m => m.PagoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
