import { Moment } from 'moment';

export interface IDomicilio {
  id?: number;
  nomeMorador?: string;
  endereco?: string;
  latitude?: string;
  longitude?: string;
  numeroHabitantes?: number;
  ligacaoDomiciliarAgua?: boolean;
  poco?: boolean;
  cisterna?: boolean;
  reservatorioElevado?: boolean;
  reservatorioSemiElevado?: boolean;
  conjuntoSanitario?: boolean;
  piaCozinha?: boolean;
  tanqueLavarRoupa?: boolean;
  filtroDomestico?: boolean;
  tanqueSeptico?: boolean;
  sumidouro?: boolean;
  valaInfiltracao?: boolean;
  sistemaReuso?: boolean;
  ligacaoDomiciliarEsgoto?: boolean;
  recipenteResiduosSolidos?: boolean;
  dataCadastro?: Moment;
  levantamentoConcluido?: boolean;
  dataAlteracao?: Moment;
  userFirstName?: string;
  userId?: number;
  localidadeDescricao?: string;
  localidadeId?: number;
}

export class Domicilio implements IDomicilio {
  constructor(
    public id?: number,
    public nomeMorador?: string,
    public endereco?: string,
    public latitude?: string,
    public longitude?: string,
    public numeroHabitantes?: number,
    public ligacaoDomiciliarAgua?: boolean,
    public poco?: boolean,
    public cisterna?: boolean,
    public reservatorioElevado?: boolean,
    public reservatorioSemiElevado?: boolean,
    public conjuntoSanitario?: boolean,
    public piaCozinha?: boolean,
    public tanqueLavarRoupa?: boolean,
    public filtroDomestico?: boolean,
    public tanqueSeptico?: boolean,
    public sumidouro?: boolean,
    public valaInfiltracao?: boolean,
    public sistemaReuso?: boolean,
    public ligacaoDomiciliarEsgoto?: boolean,
    public recipenteResiduosSolidos?: boolean,
    public dataCadastro?: Moment,
    public levantamentoConcluido?: boolean,
    public dataAlteracao?: Moment,
    public userFirstName?: string,
    public userId?: number,
    public localidadeDescricao?: string,
    public localidadeId?: number
  ) {
    this.ligacaoDomiciliarAgua = this.ligacaoDomiciliarAgua || false;
    this.poco = this.poco || false;
    this.cisterna = this.cisterna || false;
    this.reservatorioElevado = this.reservatorioElevado || false;
    this.reservatorioSemiElevado = this.reservatorioSemiElevado || false;
    this.conjuntoSanitario = this.conjuntoSanitario || false;
    this.piaCozinha = this.piaCozinha || false;
    this.tanqueLavarRoupa = this.tanqueLavarRoupa || false;
    this.filtroDomestico = this.filtroDomestico || false;
    this.tanqueSeptico = this.tanqueSeptico || false;
    this.sumidouro = this.sumidouro || false;
    this.valaInfiltracao = this.valaInfiltracao || false;
    this.sistemaReuso = this.sistemaReuso || false;
    this.ligacaoDomiciliarEsgoto = this.ligacaoDomiciliarEsgoto || false;
    this.recipenteResiduosSolidos = this.recipenteResiduosSolidos || false;
    this.levantamentoConcluido = this.levantamentoConcluido || false;
  }
}
