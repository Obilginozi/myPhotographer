import { IAlbum } from 'app/shared/model/album.model';

export interface IFile {
  id?: string;
  name?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  album?: IAlbum | null;
}

export const defaultValue: Readonly<IFile> = {};
