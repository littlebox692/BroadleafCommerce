/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate;

import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * @author Jeff Fischer
 */
@Component("blEqPredicateProvider")
public class EqPredicateProvider implements PredicateProvider<Serializable, Serializable> {

    @Override
    public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder, From root, String ceilingEntity,
                                    String fullPropertyName, Path<Serializable> explicitPath, List<Serializable> directValues) {
        Path<Serializable> path;
        if (explicitPath != null) {
            path = explicitPath;
        } else {
            path = fieldPathBuilder.getPath(root, fullPropertyName, builder);
        }
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (Serializable directValue : directValues) {
            predicates.add(builder.equal(path, directValue));
        }
        
        return builder.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}
