package odooconsumerpoc;

import java.util.List;
import java.util.Map;

public interface OdooRpcClient {

    Map<String, Object> getServerVersionInfo();

    /**
     * Calls the "authenticate" remote method to get the user's uid, this value has
     * to be sent along the password to call the rest of the remote methods.
     *
     * @return user's uid
     */
    int authenticate();

    /**
     * Get all the record Ids for an Odoo model that match the provided filter.
     *
     * @param uid            user's uid
     * @param model          Odoo Model to query
     * @param filterCriteria Filter criteria to be applied to the search
     * @return List of matching record Ids
     */
    List<Integer> findRecordIdsByModelAndFilter(int uid, String model, List<Object> filterCriteria);

    /**
     * Find record for an Odoo model by Id.
     *
     * @param uid      user's uid
     * @param model    Odoo Model to query
     * @param recordId Id of the desired record
     * @return Desired record (if available)
     */
    Map<String, Object> findRecordByIdAndModel(int uid, String model, int recordId);
}
