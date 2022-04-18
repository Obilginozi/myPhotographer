import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const File = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const fileList = useAppSelector(state => state.file.entities);
  const loading = useAppSelector(state => state.file.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="file-heading" data-cy="FileHeading">
        <Translate contentKey="fotografcimApp.file.home.title">Files</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="fotografcimApp.file.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="fotografcimApp.file.home.createLabel">Create new File</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fileList && fileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="fotografcimApp.file.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="fotografcimApp.file.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="fotografcimApp.file.photo">Photo</Translate>
                </th>
                <th>
                  <Translate contentKey="fotografcimApp.file.album">Album</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileList.map((file, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${file.id}`} color="link" size="sm">
                      {file.id}
                    </Button>
                  </td>
                  <td>{file.name}</td>
                  <td>
                    {file.photo ? (
                      <div>
                        {file.photoContentType ? (
                          <a onClick={openFile(file.photoContentType, file.photo)}>
                            <img src={`data:${file.photoContentType};base64,${file.photo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {file.photoContentType}, {byteSize(file.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{file.album ? <Link to={`album/${file.album.id}`}>{file.album.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${file.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${file.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${file.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="fotografcimApp.file.home.notFound">No Files found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default File;
