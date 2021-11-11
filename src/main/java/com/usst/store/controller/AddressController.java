package com.usst.store.controller;

import com.usst.store.entity.Address;
import com.usst.store.service.IAddressService;
import com.usst.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {

        addressService.addNewAddress(getUidFromSession(session),getUsernameFromSession(session),address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        List<Address> data = addressService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.setDefault(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid,HttpSession session) {
        addressService.delete(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
