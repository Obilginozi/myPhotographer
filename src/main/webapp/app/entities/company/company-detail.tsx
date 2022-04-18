import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">
          <Translate contentKey="fotografcimApp.company.detail.title">Company</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="fotografcimApp.company.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="companyName">
              <Translate contentKey="fotografcimApp.company.companyName">Company Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyName}</dd>
          <dt>
            <span id="companyPhone">
              <Translate contentKey="fotografcimApp.company.companyPhone">Company Phone</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyPhone}</dd>
          <dt>
            <span id="companyMail">
              <Translate contentKey="fotografcimApp.company.companyMail">Company Mail</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyMail}</dd>
          <dt>
            <span id="companyAddress">
              <Translate contentKey="fotografcimApp.company.companyAddress">Company Address</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyAddress}</dd>
          <dt>
            <Translate contentKey="fotografcimApp.company.user">User</Translate>
          </dt>
          <dd>{companyEntity.user ? companyEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
