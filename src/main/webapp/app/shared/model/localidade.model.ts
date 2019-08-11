import { Moment } from 'moment';

export interface ILocalidade {
  id?: number;
  descricao?: string;
  abastecimentoAgua?: boolean;
  esgotoSanitario?: boolean;
  coletaResiduos?: boolean;
  dataAlteracao?: Moment;
  userFirstName?: string;
  userId?: number;
  cidadeDescricao?: string;
  cidadeId?: number;
}

export class Localidade implements ILocalidade {
  constructor(
    public id?: number,
    public descricao?: string,
    public abastecimentoAgua?: boolean,
    public esgotoSanitario?: boolean,
    public coletaResiduos?: boolean,
    public dataAlteracao?: Moment,
    public userFirstName?: string,
    public userId?: number,
    public cidadeDescricao?: string,
    public cidadeId?: number
  ) {
    this.abastecimentoAgua = this.abastecimentoAgua || false;
    this.esgotoSanitario = this.esgotoSanitario || false;
    this.coletaResiduos = this.coletaResiduos || false;
  }
}
